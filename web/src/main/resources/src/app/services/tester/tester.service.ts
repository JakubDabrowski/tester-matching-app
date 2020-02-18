import {AppConstants} from "../../constants/constants.component";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Tester} from "./tester.model";
import {URLSearchParams} from "@angular/http";

@Injectable()
export class TesterService {

  private readonly API_URL: string = AppConstants.apiUrl + '/tester';

  constructor(private http: HttpClient){
  }

  getTesters (countries: string[], devices: number[]): Observable<Tester[]>{
    let params = new URLSearchParams();

    if(countries.length > 0 && !(countries.indexOf("-1") > -1)){ // if not selected all countries
      params.append('country', countries.join(', '));
    }

    if(devices.length > 0 && !(devices.indexOf(-1) > -1)){ // if not selected all devices
      params.append('device', devices.join(', '));
    }

    let parametersText = params.toString();
    if(parametersText){
      return this.http.get<Tester[]>(this.API_URL + "/search?" + parametersText, AppConstants.httpOptions);
    } else {
      return this.getAllTesters();
    }
  }

  getAllTesters (): Observable<Tester[]>{
    return this.http.get<Tester[]>(this.API_URL, AppConstants.httpOptions);
  }
}
