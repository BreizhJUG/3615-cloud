#! /bin/bash

export NXT_HOME=/Users/nicolas/devoxx/3615-cloud/lego/
export PATH=$PATH:$NXT_HOME/bin

nxjc HelloWorld.java
nxj -r HelloWorld
