# Menu i nawigacja
## Co się stanie, jeśli w menu_main.xml zamienisz app:showAsAction na android:showAsAction? 
Opcje ifRoom i always przestają działać, działa tylko never

## Dlaczego ostatnia linia onOptionsItemSelected to return super.onOptionsItemSelected(item), a nie return false?
Aby móc obsłużyć podstawowo obsługiwane opcje przez klase nadrzędną (np. strzałka)