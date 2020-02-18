import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";

import {RouterModule} from "@angular/router";
import {AppComponent} from "./app.component";

import { HeaderComponent } from './header/header.component';
import {RankingComponent} from "./ranking/ranking.component";
import {LoginComponent} from "./login/login.component";
import {RankingTableComponent} from "./ranking/ranking-table/ranking-table.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTableModule, MatPaginatorModule, MatSortModule, MatFormFieldModule, MatInputModule, MatSelectModule} from "@angular/material";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    RankingComponent,
    RankingTableComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    RouterModule.forRoot([
      { path: '', component: RankingComponent },
      { path: 'login', component: LoginComponent }
    ]),
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
