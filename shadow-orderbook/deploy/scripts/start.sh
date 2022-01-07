#!/bin/bash

version=current
if [[ ${1+x} ]]
then
    version=$1
fi

echo "Starting $version"    
java -jar $version

#!/bin/bash

version=current
if [[ ${1+x} ]]
then
	    version=$1
fi

echo "Starting $version"    
java -jar $version


