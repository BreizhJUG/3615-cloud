#! /bin/bash

export NXT_HOME=/Users/nicolas/devoxx/3615-cloud/lego/
export PATH=$PATH:$NXT_HOME/bin
export DYLD_LIBRARY_PATH=$NXT_HOME/lib

nxjpcc Server.java
nxjpc Server
