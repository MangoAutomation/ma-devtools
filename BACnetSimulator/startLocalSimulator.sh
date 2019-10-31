#Execute this script from the maven-target/test-classes directory to run simulator
#
# Add all of the jar files in the ../lib dir to the classpath
    for f in ../../lib/*.jar
    do
      MA_CP=$MA_CP:$f
    done

java -cp $MA_CP \
com/infiniteautomation/bacnet/BacNetLocalSimulator \
