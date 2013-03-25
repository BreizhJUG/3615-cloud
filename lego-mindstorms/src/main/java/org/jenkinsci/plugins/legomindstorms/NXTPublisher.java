package org.jenkinsci.plugins.legomindstorms;
import hudson.Launcher;
import hudson.Extension;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.util.ListBoxModel;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.DataOutputStream;
import java.io.IOException;

import static hudson.model.Result.SUCCESS;
import static lejos.pc.comm.NXTCommFactory.BLUETOOTH;
import static lejos.pc.comm.NXTCommFactory.USB;

public class NXTPublisher extends Notifier {

    private final String connection;

    private final String name;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public NXTPublisher(String connection, String name) {
        this.connection = connection;
        this.name = name;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        if (build.getResult().isBetterOrEqualTo(SUCCESS)) {
            return true;
        }

        try {
            NXTComm nxtComm = NXTCommFactory.createNXTComm(
                    connection.equals("USB") ? USB : BLUETOOTH);
            NXTInfo[] nxt = nxtComm.search(name);
            nxtComm.open(nxt[0]);
            DataOutputStream out = new DataOutputStream(nxtComm.getOutputStream());
            out.writeUTF("BUILD FAILED");
            out.flush();
            out.close();
        } catch (NXTCommException e) {
            e.printStackTrace(listener.getLogger());
            throw new InterruptedException();
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public String getConnection() {
        return connection;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.BUILD;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Notify Lego Mindstorms robot";
        }
    }
}

