# Makefile
checkstyle-config = ../../SWAConfig.xml

style : 
	checkstyle -c $(checkstyle-config) src/main/java
