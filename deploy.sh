HOST="donghyukki@192.168.1.15"
./gradlew clean bootJar
sshpass -f /Users/kakao_ent/study/password.txt scp -r build/libs/*.jar $HOST:/home/donghyukki
sshpass -f /Users/kakao_ent/study/password.txt ssh $HOST "cd /home/donghyukki; ./start.sh"
exit 0;