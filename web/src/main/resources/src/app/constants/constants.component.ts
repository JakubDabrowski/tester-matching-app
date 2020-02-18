import {Cookie} from "ng2-cookies";
import {HttpHeaders} from "@angular/common/http";

export class AppConstants {

  public static get baseUrl(): string { return "http://localhost:8001/"; }
  public static get apiUrl(): string { return AppConstants.baseUrl + "protected/api"; }

  public static get httpOptions() {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + Cookie.get('access_token')
      })
    };
  }
}
