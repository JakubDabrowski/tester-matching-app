import {Component} from "@angular/core";
import {AppService} from "../app.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'login-form',
  providers: [AppService],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  userData = new FormGroup({
    username: new FormControl(''),
    password: new FormControl('')
  });

  constructor(private _service:AppService) {
  }

  login() {
    this._service.obtainAccessToken(this.userData.value);
  }
}
