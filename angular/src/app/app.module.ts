import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ElcdComponent } from './components/elcd/elcd.component';
import { HttpClientModule } from '@angular/common/http';
import { FooterComponent } from './components/footer/footer.component';
import { TelegramComponent } from './components/telegram/telegram.component';
import { FormsModule } from '@angular/forms';
import { SidebarModule } from 'ng-sidebar';
import { ToDoLiComponent } from './components/todolist/todol.component';

@NgModule({
  declarations: [
    AppComponent,
    TelegramComponent,
    FooterComponent,
    ElcdComponent,
    ToDoLiComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    SidebarModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
