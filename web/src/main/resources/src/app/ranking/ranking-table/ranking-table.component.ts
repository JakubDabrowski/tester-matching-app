import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from "@angular/material";
import {DeviceService} from "../../services/device/device.service";
import {FormControl, FormGroup} from "@angular/forms";
import {CountryService} from "../../services/country/country.service";
import {Device} from "../../services/device/device.model";
import {Country} from "../../services/country/country.model";
import {TesterService} from "../../services/tester/tester.service";
import {Tester} from "../../services/tester/tester.model";

@Component({
  selector: 'ranking-table',
  providers: [DeviceService, TesterService],
  templateUrl: 'ranking-table.component.html',
  styleUrls: ['ranking-table.component.css']
})

export class RankingTableComponent implements OnInit {
  displayedColumns: string[] = ['rank', 'name', 'experience'];
  testers = new MatTableDataSource<Tester>();

  public countries: Country[] = [];
  public devices: Device[] = [];

  constructor(private deviceService:DeviceService, private testerService:TesterService) {}

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.testerService.getAllTesters().subscribe((testers) => {
      this.updateTestersData(testers);
    });

    this.countries = CountryService.getAllCountries();

    this.deviceService.getAllDevices().subscribe((devices) => {
      this.devices = devices;
    });
  }

  tableFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.testers.filter = filterValue.trim().toLowerCase();
  }

  testerSearchForm = new FormGroup({
    selectedCountries: new FormControl(''),
    selectedDevices: new FormControl('')
  });
  filterResults(){
    this.testerService.getTesters(this.testerSearchForm.value["selectedCountries"], this.testerSearchForm.value["selectedDevices"]).subscribe((testers) => {
      this.updateTestersData(testers);
    });
  }

  private updateTestersData(testers) {
    console.log("testers", testers);
    this.testers = new MatTableDataSource<Tester>(testers);

    this.testers.paginator = this.paginator;
    this.testers.sort = this.sort;
  }
}
