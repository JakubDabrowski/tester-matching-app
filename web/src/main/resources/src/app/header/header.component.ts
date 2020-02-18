import {Component, OnInit} from "@angular/core";
import {AppService} from "../app.service";

@Component({
  selector: 'app-header',
  providers: [AppService],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(
    private appService:AppService){}

  ngOnInit(){
    this.appService.checkCredentials();
  }

  logout() {
    this.appService.logout();
  }

  checkIfHasAccessToken(){
    return this.appService.checkIfHasAccessToken();
  }
}
