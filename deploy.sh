#!/usr/bin/env bash

TOOLS_PATH="tools"

FIREFOX_ARCHIVE_PATH=`find tools/ -name firefox-*.tar.bz2`
echo "[INFO]: == Début de l'installation de ${FIREFOX_ARCHIVE_PATH} =="
tar -jxvf ${FIREFOX_ARCHIVE_PATH} -C ${TOOLS_PATH}
export FIREFOX_BIN=`find tools -name firefox-bin`
echo "[INFO]: == Fin de l'installation =="


GECKODRIVER_ARCHIVE_PATH=`find tools/ -name geckodriver-*.tar.gz`
echo "[INFO]: == Début de l'installation de ${GECKODRIVER_ARCHIVE_PATH} =="
tar -zxvf ${GECKODRIVER_ARCHIVE_PATH} -C ${TOOLS_PATH}
export WEBDRIVER_BIN=`find tools -name geckodriver`
echo "[INFO]: == Fin de l'installation =="


