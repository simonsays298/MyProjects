=============================TEMA2-VCS===========================
Serbanoiu Simona 321CD

Am inceput implementarea temei prin a trata in Context toate cazurile posibile de comezi ce apar atat pentru filesystem cat si pentru vcs. Astfel, pentru fiecare functie de tipul vcs am creat cate o clasa :
-StatusOperation -> in care afisez branch-ul curent, precum si ce
comenzi de filesystem au fost date au fost date 
-Branch si BranchOperation -> unde in Branch imi intializez o lista cu toate commit-urile existente, precum si numele branch-ului, dar si o metoda ce imi verifica daca un branch a primit commit. In BranchOperation adaugam un nou branch numai daca el nu exitsa, altfel primim eroare.
-Commit si CommitOperation -> unde in Commit imi retin id-ul, mesajul si snaphost-ul commit-ului curent. In CommitOperation, verificam daca avem ceva in lista de comenzi de filesystem, daca da golim tot, cream mesaj daca este cazul, iar apoi cream un nou commit, pe care il adaugam in branch-ul curent. Daca lista de comezi de filesystem este goala atunci primim eroare.
-LogOperation -> verficam ce branch-uri au lista de commit-uri nenula si afaisam id-ul si mesajul al fiecarui commit in parte
-RollbackOperation -> actualizam snapshot-ul lui vcs la ultimul commit din branch-ul current
-CheckoutOperation -> daca avem checkout simplu atunci verificam daca branch-ul curent exista, daca nu, atunci intoarcem eroare, altfel verificam ca lista de comenzi de filesystem (modificari) sa fie goala si setam branch-ul curent la cel dat ca input. Daca avem si parametrul -c, verificam daca in branch-ul curent exista id-ul commit-ului dat ca input, daca da stergem toate commit-urile de dupa si actualizam snapshot-ul vcs-ului cu cel al comitului. Daca nu exista si lista de comenzi de filesystem este 0, atunci intoarcem eroate, iar daca lista de comenzi nu e goala iar intoarcem eroare.