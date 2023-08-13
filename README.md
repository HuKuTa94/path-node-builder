# path-node-builder

Deployed on Heroku:
https://peaceful-cove-76019.herokuapp.com/

Domain Driven Design - Единый язык

Overwatch - название онлайн игры.

Workshop - мастерская игры, в которой игроки могут делать собственные игровые режимы.

snippet - исходный код игрового режима. Содержит правила, переменные и свойста игрового режима.

array, массив - тип данных мастерской. Может содержать внутри себя другие типы.

vector, position - тип данных мастерской. Содержит 3 числа-координаты x, y, z.

mod, game mode - мод или игровой режим, созданный игроками игры в мастерской.

path node builder - мод мастерской, который позволяет создавать навигационные пути для ИИ ботов.
Производит входные данные (incoming snippet) для расчета матрицы расстояния.

user, пользователь - человек, который является игроком и/или разработчиком модов для мастерской игры. 
Является отправителем incoming snippet.

incoming snippet - входящие данные из игрового режима мастерской path node builder в виде исходного кода.
Содержит неоптимальные данные для расчета матрицы расстояний. Неоптимальны они потому, что в процессе строительства 
навигационных путей в игровом режиме path node builder пользователь может удалять узлы. 
На месте удаленного узла в position array и connection array образуется "дырка" в виде слова "False". 
Это сделано для оптимальной работы режима path node builder, чтобы не усложнять реализацию и сохранить порядок
существующих узлов.

validation - проверка incoming snippet на наличие требуемых переменных и необходимых данных

optimized incoming snippet / оптимизированные входящие данные - это обработанный incoming snippet,
в котором удалены дырки и перерасчитаны id узлов в position array и connection array.
Такие данные готовы для расчета матрицы расстояний

outgoing snippet - исходящие данные из приложения в виде исходного кода с расчитанной матрицей расстояний для импорта
в мастерскую

hole, дырка - в incoming snippet так называется удаленный нод из position array и connection array, вместо его
числового id
в массивах содержится слово False.

node, path node - навигационный узел (нод). 
Содержит: 
- координаты расположения в 3D пространстве в виде вектора,
- связи/соединения с другими нодами, 
- id нода (индекс нода в position array).

position array - массив расположения (позиций) всех нодов.

connection - соединение/связь нода в виде массива id'шников нодов. Нод может быть соединен с другими нодами. 
По соединенным нодам могут двигаться ИИ боты.

connection array - двумерный массив, который содержит все связи нодов. Структура массива:
[0]{1, 2}
[1]{0, 2}
[2]{1}
...
Первый массив {0, 1, 2} содержит id нодов, второй массив содержит id, с которым соединен нод. 
Наример: нод с id=0 соединен с нодами id=1 и id=2, a нод c id=2 соединен только с нодом id=1 и т.д.

bot, AI, ИИ - бот, искусственный интелект. Представляет из себя персонажа-балванчика игры,
который может прокладывать путь в 3D пространстве, используя навигационные пути (ноды), созданные в моде
path node builder.

distance matrix - двумерная матрица расстояний. Содержит рассчитанные расстояния (без единиц измерения) между всеми
навигационными узлами.

converting - конвертация данных предметной области.


Ограниченные контексты (Bounded context)
Workshop
Расчет матрицы расстояний

Предметная область (Domain)
Моды мастерской Workshop. Предоставление небольшого и максимального оптимизированного фрагмента исходного 
кода мастерской, который содержит позиции нодов, их связи и рассчитанную на их основе матрицу расстояний.

Смысловое ядро (Core subdomains)
Конвертация расчитанной матрицы расстояний в исходящий исходный код мастерской:
-Валидация входящего исходного кода
-Конвертация входящего исходного кода в модель
-Оптимизация сконвертированной модели


Служебная подобласть (Supporting subdomain) - определенный аспект бизнеса, который важен, но не является смысловым ядром.
Расчет матрицы расстояний
