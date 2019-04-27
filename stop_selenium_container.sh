#!/usr/bin/env bash

#TOOLS_PATH="tools"
#
#FIREFOX_ARCHIVE_PATH=`find tools/ -name firefox-*.tar.bz2`
#echo "[INFO]: == Début de l'installation de ${FIREFOX_ARCHIVE_PATH} =="
#tar -jxvf ${FIREFOX_ARCHIVE_PATH} -C ${TOOLS_PATH}
#export FIREFOX_BIN=`find tools -name firefox-bin`
#echo "[INFO]: == Fin de l'installation =="
#
#
#GECKODRIVER_ARCHIVE_PATH=`find tools/ -name geckodriver-*.tar.gz`
#echo "[INFO]: == Début de l'installation de ${GECKODRIVER_ARCHIVE_PATH} =="
#tar -zxvf ${GECKODRIVER_ARCHIVE_PATH} -C ${TOOLS_PATH}
#export WEBDRIVER_BIN=`find tools -name geckodriver`
#echo "[INFO]: == Fin de l'installation =="

# PARAMETERS
SELENIUM_FIREFOX_NODE_VERSION=${SELENIUM_FIREFOX_NODE_VERSION:3.11.0}
SELENIUM_HUB_VERSION=${SELENIUM_HUB_VERSION:3.11.0}
SELENIUM_HUB_PORT=${SELENIUM_HUB_PORT:4444}

echo "Stopping selenium hub v${SELENIUM_HUB_VERSION} container"
docker stop selenium-hub && docker rm selenium-hub

echo "Stopping firefox node v${SELENIUM_FIREFOX_NODE_VERSION} container"
docker stop firefox-node && docker rm firefox-node
