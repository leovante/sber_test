Шаги для обработки новой заявки:
1) Смотрим атрибут book. Если такой стакан не существует, создаем его.
2) Смотрим, приводит ли постановка заявки в стакан к ее исполнению (matching).
3) Если заявка исполняется полностью, то она удаляется. Также удаляется встречная, ранее
поставленная заявка.
4) Если заявка исполняется частично, то обновляются обе - текущая и встречная заявки.
Остаток заявки ставится в стакан.
5) Если заявка не исполняется – просто ставится в стакан. 

Шаги для обработки заявки удаления:
1) Смотрим атрибут book, находим стакан.
2) Находим нужную заявку по orderId.
3) Если нашли – удаляем.

Ответы
1) строки заявок в xml файле парсятся с помощью JAXB в обьекты и добавляются в стаканы
2) метод OrderBook.matchOrders() ... 5.
-----
1) метод ParseToBooks.setDeleteOrder()
2) создана специальная структура Map<Integer, Double> orderId_priceMap;
3) метод orderList.remove(i);

Что можно улучшить:
1) Сделать валидатор на загружаемый файл
2) Добавить распараллеливание обработки заявки
3) Выделить отдельно логику управления заявками
4) Нужно ускорить удаление заявки. Определив конкретное место в коллекции.
Сейчас обычный перебор листа.
