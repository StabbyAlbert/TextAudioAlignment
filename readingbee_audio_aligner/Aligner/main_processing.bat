
C:/java/long_audio_aligner/Aligner/ffmpeg -i C:/tomcat5.0.30_old/webapps/ROOT/txt/pride_and_prejudice_librivox/%file1%.mp3 -acodec pcm_s16le -ar 16000 -ab 16000 -ac 1 C:/tomcat5.0.30_old/webapps/ROOT/txt/pride_and_prejudice_librivox/%file1%.wav
cd \ && cd /java/long_audio_aligner/Aligner && C:/java/jdk1.6.0_14/bin/java -Xms512m -Xmx1024m -classpath C:/java/long_audio_aligner/Aligner/*;C:/java/long_audio_aligner/Aligner/lib/* alignment.AlignMainNew "C:/tomcat5.0.30_old/webapps/ROOT/txt/pride_and_prejudice_librivox/" "%file1%.wav" 0 0 0 14 2 0 120 19

