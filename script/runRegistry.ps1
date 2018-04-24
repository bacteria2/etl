$javaOPT=" -xms:512mb "
$customOPT=" --spring.profiles.active="
$profiles="peer1,peer2,peer3"
if($env:JAVA_HOME -ne ""){
    foreach($item in $profiles.split(",")){
        echo start $env:JAVA_HOME"\bin\java.exe -jar ./registryCenter.jar "$customOPT$item $JavaOPT
        Start-Process -FilePath "$env:JAVA_HOME\bin\java.exe"  -ArgumentList "-jar .\registryCenter.jar $customOPT$item $JavaOPT"
    }
}else{
    echo "please check if environment variable JAVA_HOME exists"
}
Read-Host -Prompt "Press Enter to continue"

