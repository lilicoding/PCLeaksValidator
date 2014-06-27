#! /bin/sh

# ./runPCLeaksValidator.sh /Users/li.li/Project/github/DroidBench/apk/InterAppCommunication_startActivity1/InterAppCommunication_startActivity1_source.apk /Users/li.li/Project/github/android-platforms '<lu.uni.serval.iac_startactivity1_source.OutFlowActivity:*void*onCreate(android.os.Bundle)>' 1 12

appPath=/Users/li.li/Project/github/DroidBench/apk/InterAppCommunication_startActivity1/InterAppCommunication_startActivity1_source.apk
androidJars=/Users/li.li/Project/github/android-platforms
methodSignature='<lu.uni.serval.iac_startactivity1_source.OutFlowActivity:*void*onCreate(android.os.Bundle)>'
isIcc=1
jimpleIndex=12

./runPCLeaksValidator.sh $appPath $androidJars $methodSignature $isIcc $jimpleIndex