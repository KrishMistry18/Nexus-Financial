#!/usr/bin/env bash
set -euo pipefail

echo "== Build Bank Management System (Linux/macOS) =="
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p out
javac -encoding UTF-8 -cp "lib/*" -d out src/bank/management/system/*.java
mkdir -p out/icon
cp -R src/icon/* out/icon/
echo "Running..."
java -cp "out:lib/*" bank.management.system.Login


