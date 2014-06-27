#! /bin/sh

cd ../

android update project -p workspace -n MaliciousApp
cd workspace
ant release