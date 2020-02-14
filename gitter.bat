#!/bin/bash

echo.
echo [ Automated GIT COMMIT on %time% ]
echo.

git status
git add .
git commit -m "Automated hourly commit"
git push

echo.

timeout 3600 > nul