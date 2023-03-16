# Location-Manager-Demo
Демонстрационный репозиторий для курса Разработка мобильных приложений на Kotlin IT Академии Samsung на площадке РТУ МИРЭА

## Сборка проекта
### Получения ключа Яндекс карт
Для работы Яндекс карт необходим ключ API. Получить его можно в [консоли разработчика Яндекса](https://developer.tech.yandex.ru/services/):
1. Подключить API
1. MapKit – мобильный SDK

### Подключение ключа в проект
Для подключения ключа необходимо:
1. Переключиться в режим просмотра Project:  
![image](https://user-images.githubusercontent.com/44675043/225652809-01ec5196-395f-448e-a064-b7cadd33aac4.png)
1. В корневой директории создать файл `config.properties`. **Этот файл ни в коем случае нельзя коммитить в открытый репозиторий**  
![image](https://user-images.githubusercontent.com/44675043/225653592-c79409ed-c249-46f3-a905-34f12385c49a.png)
1. Добавить в созданный файл поле `MAPS_API_KEY`:
```
MAPS_API_KEY="<Ваш ключ>"
```
4. Пересобрать проект

### Как это работает?
В файле `build.gradle` модуля на основе файла `config.properties` генерируется объект `Properties`, значение по ключу `MAPS_API_KEY` которого добавляется как поле в
генерируемый при сборке класс `BuildConfig`. 
```groovy
android {
    // ...

    defaultConfig {
        // ...
        
        
        def keystorePropertiesFile = rootProject.file("config.properties")
        def keystoreProperties = new Properties()
        keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
        buildConfigField("String", "MAPS_API_KEY", keystoreProperties['MAPS_API_KEY'])
    }
    // ...
}
```
Аргументы функции `buildConfigField()` указывают Java-тип поля (String), имя поля (MAPS_API_KEY) и значение поля соответственно.

В переопределённом классе `Application`, как того требует документация Yandex MapKit, ключ, находящийся теперь в классе `BuildConfig`, передаётся в библиотеку.
```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY);
    }
}
```
### Зачем именно так? 
Затем, что любые API-ключи не должны быть доступны внешним акторам. Благодаря тому, что `config.properties` игнорируется гитом (файл `.gitignore`), он не может попасть 
в публичные репозитории и остаётся на машине разработичков.  
Именно благодаря такому подходу ни у кого из вас, дорогие студенты, нет возможности узнать мой ключ Яндекс карт.
