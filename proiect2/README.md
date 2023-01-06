Dolineanu Miruna, grupa 321CD;
Proiect POO etapa 2 - POO TV

__Continut__

Acest fisier contine detalii despre implementarea codului
pentru cea de-a doua etapa a proiectului. Pentru functionalitatile
din prima etapa am folosit codul din acea etapa, cu mici modificari.

__Design pattern-uri folosite__

Pe langa pattern-ul _Visitor_ pe care l-am folosit in prima etapa
a proiectului, in aceasta etapa am folosit si _Command, Observer si
Factory Patterns_.

__Factory Pattern__

Acest pattern a fost folosit pentru instantierea mai usoara a
diferitelor tipuri de pagini (prin clasa PageFactory), in special 
pentru actiunea de change page.

__Command Pattern__

Acest pattern a fost folosit pentru implementarea actiunii de "back"
intre pagini. Clasa ChangeCommand implementeaza acest pattern. Metoda
"execute" din cadrul acesteia executa actiunea de change page
(implementata in cadrul etapei trecute cu ajutorul pattern-ului
Visitor), iar metoda "undo" executa actiunea de "back". Pentru
aceasta, clasa contine o lista de pagini care memoreaza istoricul
de pagini accesate de user.

__Observer pattern__

Acest pattern a fost folosit pentru a implementa actiunile de
tipul "database" pentru adaugarea/stergerea filmelor. Observable
este clasa DatabaseAction, iar observerii sunt userii din lista
de useri din input. DatabaseAction cuprinde trei metode:
- metoda "addMovie" - adauga un film in database-ul de filme din
input si notifica userii ce au dat subscribe la genurile filmului
adaugat.
- metoda "deleteMovie" - sterge filmul din database si notifica
userii ce au cumparat filmul.
- metoda "execute" - decide care dintre metode trebuie executata.

__Actiunea de subscribe__

A fost implementata in cadrul clasei OnPage, in metoda visit a 
paginii de tipul "See Details". Adauga genul precizat in lista
de subscribedGenres a userului.

__Noi metode in clasa User__

- notifyUserAdd - notifica user-ul de adaugarea filmului dat;
- notifyUserDelete - notifica user-ul de stergerea filmului dat;
- refundUser - returneaza resursele user-ului;
- removeMovie - sterge filmul din listele user-ului.

__Actiunea de recomandare__

La sfarsitul parcurgerii actiunilor este apelata metoda recommend
din clasa Recommendation. Aceasta creeaza o notificare cu filmul
recomandat (sau "No recommendation" daca nu exista filme care au
primit like de la user, deci nu se poate creea o lista de genuri
preferate) doar daca user-ul care este logat la finalul listei de
actiuni are cont de tip premium.