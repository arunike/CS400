#!/usr/bin/env bash
echo "Content-type: text/html"
echo ""
java CustomProgram "$QUERY_STRING" 2>&1
