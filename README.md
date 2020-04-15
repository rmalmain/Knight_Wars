# Knight Wars
Knight Wars gameplay centers on capturing castles and other buildings by armies overwhelming the defending forces.

![Menu screen](/android/assets/preview/menu-screen.jpg)

## Run on desktop (Linux)
```
git clone https://github.com/rmalmain/Knight_Wars.git
cd Knight_Wars
./gradlew desktop:run
```
## Run on desktop (Windows)
```
git clone https://github.com/rmalmain/Knight_Wars.git
cd Knight_Wars
./gradlew.bat desktop:run
```

## Troubleshoot

### Error "Could not find tools.jar..."
Add the following line to gradle.properties
```
org.gradle.java.home=PATH_TO_JAVA_1.8_JDK
```
### Error "Could not reserve enough space for 1536000KB object heap"
Please try to lower in gradle.properties the number in option '-Xmx1500m' in the following line :
```
org.gradle.jvmargs=-Xms128m -Xmx1500m
```
