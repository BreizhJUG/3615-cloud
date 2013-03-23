import java.io.*;
import java.util.*;
import lejos.pc.comm.*;

public class Server {

    public static void main(String[] args) throws Exception {
       NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH); 
       NXTInfo[] nxt = nxtComm.search("NXT");
       nxtComm.open(nxt[0]);
       DataOutputStream out = new DataOutputStream(nxtComm.getOutputStream());
       out.writeUTF("BUILD FAILED");
       out.flush();
       out.close();
    }
}
