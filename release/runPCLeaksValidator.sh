#! /bin/sh

# preparing the environment of automatically generating android applications

rm -rf MaliciousApp-release.apk
cd ../

rm -rf workspace
cp -r template workspace

cd scripts

java -cp PCLeaksValidator.jar lu.uni.snt.pcleaks.validator.wrapper.MainWrapper $1 $2 $3 $4 $5

cd ../

android update project -p workspace -n MaliciousApp
cd workspace
ant release

cp bin/MaliciousApp-release.apk ../release