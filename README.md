# Knight Wars
Knight Wars gameplay centers on capturing castles and other buildings by armies overwhelming the defending forces.

## Run on desktop
```
git clone https://github.com/rmalmain/Knight_Wars.git
cd Knight_Wars
./gradlew desktop:run
```
## Run on Windows
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
