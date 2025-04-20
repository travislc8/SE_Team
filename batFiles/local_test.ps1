./compile.ps1
wt -w 3 --title "server"  -d ./ pwsh "server.ps1" "noCompile"
timeout \t 3000 \nobreak
wt -w 3 --title "client 1" -d ./ pwsh "client.ps1" "noCompile"
wt -w 3 --title "client 2" -d ./ pwsh "client.ps1" "noCompile"
