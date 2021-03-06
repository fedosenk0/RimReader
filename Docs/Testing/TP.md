# **1.  Введение**
Основная цель данного документа состоит в том, чтобы описать план тестирования приложения "RimReader ". Эта информация предназначена для людей, которые будут тестировать данное программное обеспечение на соответствие требованиям.
# **2.	Объект тестирования**
В качестве объекта тестирования следует выделить функциональное тестирование, а также тестирование удобства и простоты использования. К атрибутам качества относятся следующие характеристики:
1.	Функциональная корректность
2.	Удобство использования
3.	Защищенность от ошибок пользователя
# **3.	Риски**
Приложение может корректно работать только при наличии у пользователя подключения к сети Интернет. К рискам, способным повлиять на работоспособность приложения можно отнести:
1. Низкая скорость или отсутсвие интернет-соединения у пользователя
2. Прекращение работы сайта переводчика
# **4.  Аспекты тестирования**
В процессе тестирования предполагается проверить соответствие системы требованиям, на основе которых она была спроектирована и реализована. Для данного проекта существуют следующие функциональные требования:
1. Работать с файлами формата fb2, txt.
2. Сохранять ранее открытые файлы.
3. Загружать новые файлы из проводника.
4. Переводить выделенное слово.

Также необходимо провести тестирование нефункциональных требований:

1. Инструменты для работы с текстом находятся в главном окне.
2. Приложение должно поддерживаться компьютерами с ОС Windows 10.
3. Для использования функции перевода необходимо подключение к интернету.
# **5. Подходы к тестированию**
Для тестирования приложения необходимо использовать ручное тестирование, чтобы проверить все аспекты тестирования.
# **6. Представление результатов**
Результаты тестирования представлены в документе "TR.md".
# **7.  Выводы**
Данный тестовый план позволяет протестировать основной функционал приложения. Успешное прохождение всех тестов не гарантирует полной работоспособности на всех платформах и архитектурах, однако позволяет полагать, что данное программное обеспечение работает корректно и является удобным в использовании.

Все предложенные тесты пройдены успешно, кроме теста № 1 "Работа с файлами формата fb2,txt", что свидетельствует о не полностью реализованом функционале приложения. Допольнительно была протестирована функция перевода предложения. Данный тест прошел успешно.  Предложения по доработке приложения:
- Работа с файлами формата fb2 и txt
- Возможность изменять шрифты
