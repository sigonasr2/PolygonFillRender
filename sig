export PROJECT_NAME="PolygonFill"
export PROJECT_DIR="src/sig"
export MAIN_CLASS="sig.${PROJECT_NAME}"
export OUT_DIR="bin"


if [ -z "$1" ]
  then
    echo ""
    echo "  Usage: ./sig <command> {args}"
    echo ""
    printf "====\tCurrent Configuration"
    printf "\t====================="
    printf "\n\t%-15s%20s" PROJECT_NAME ${PROJECT_NAME}
    printf "\n\t%-15s%20s" PROJECT_DIR ${PROJECT_DIR}
    printf "\n\t%-15s%20s" MAIN_CLASS ${MAIN_CLASS}
    printf "\n\t%-15s%20s" OUT_DIR ${OUT_DIR}
    printf "\n====================================================="
    echo ""
    echo ""
    echo "  Command List:"
    ls -1 ./scripts | sed -e 's/\.sh$//' | sed -e 's/^/    /'
    echo ""
    exit
fi

./scripts/$1.sh "${*:2}"