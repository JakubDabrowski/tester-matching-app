import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {Cookie} from "ng2-cookies";
import {URLSearchParams} from "@angular/http";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import {AppConstants} from "./constants/constants.component";
import {HttpClient} from "@angular/common/http";
import {HttpHeaders} from "@angular/common/http";

@Injectable()
export class AppService {
  constructor(
    private _router: Router, private _http: HttpClient){}

  obtainAccessToken(loginData){
    let params = new URLSearchParams();
    params.append('username',loginData.username);
    params.append('password',loginData.password);
    params.append('grant_type','password');
    params.append('client_id','testerMatchingAppClient');

    let options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'Basic '+btoa("testerMatchingAppClient:testerMatchingAppSecret")
      })
    };

     this._http.post(AppConstants.baseUrl + 'oauth/token', params.toString(), options)
    .subscribe(
      data => this.saveToken(data),
      err => alert('Invalid Credentials')
    );
  }

  saveToken(token){
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set("access_token", token.access_token, expireDate);
    this._router.navigate(['/']);
  }

  checkCredentials(){
    if (!this.checkIfHasAccessToken()){
        this._router.navigate(['/login']);
    }
  }

  checkIfHasAccessToken(){
    return Cookie.check('access_token');
  }

  logout() {
    Cookie.delete('access_token');
    this._router.navigate(['/login']);
  }
}
