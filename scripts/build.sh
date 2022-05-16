rm -Rf out/*
javac -Xlint:unchecked -cp ${PROJECT_DIR}/.. -d ${OUT_DIR} ${PROJECT_DIR}/*.java
printf "\n\n\nRunning Program...\n\n"
cd $OUT_DIR
java ${MAIN_CLASS} "$@"
../scripts/clean.sh