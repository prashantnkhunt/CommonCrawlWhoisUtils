git pull;
mvn clean install;
nohup java -Xms10g -Xmx10g  -jar ./target/CommonCrawlWhoisUtils-1.0-SNAPSHOT.jar > output-25-11-2020.log &

