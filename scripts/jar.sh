MAIN_CLASS="sig.PolygonFill"

rm -Rf bin/*
javac -Xlint:unchecked -cp src -d bin ${PROJECT_DIR}/${PROJECT_NAME}.java
printf "\n\n\nGenerating Manifest...\n\n"
touch manifest
echo "Main-Class: ${MAIN_CLASS}" >> manifest
printf "\n\n\nCreating Jar...\n\n"
cd ${OUT_DIR}
jar cfm ${PROJECT_NAME}.jar ../manifest sig
printf "\n\n\nRunning Program...\n\n"
java -jar ${PROJECT_NAME}.jar
mv ${PROJECT_NAME}.jar ..
cd ..
./scripts/clean.sh