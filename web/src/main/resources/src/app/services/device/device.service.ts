import {AppConstants} from "../../constants/constants.component";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Device} from "./device.model";

@Injectable()
export class DeviceService {

  private readonly API_URL: string = AppConstants.apiUrl + '/device';

  constructor(private http: HttpClient){
  }

  getAllDevices (): Observable<Device[]>{
    return this.http.get<Device[]>(this.API_URL, AppConstants.httpOptions);
  }
}
