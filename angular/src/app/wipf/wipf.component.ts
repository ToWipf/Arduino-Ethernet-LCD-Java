import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-wipf',
  templateUrl: './wipf.component.html',
  styleUrls: ['./wipf.component.less']
})
export class WipfComponent implements OnInit {

  constructor(
    private http: HttpClient,
  ) { }

  public txt: string;
  public online: string = "ff";

  ngOnInit() {
    this.http.get('http://192.168.2.10:8080/status/').subscribe((data) =>{
      console.log(data);
      this.online = data.toString();
    });
  }

  public sendMsg(): void {
    console.log(this.txt);
    this.http.put('http://192.168.2.10:8080/msg/' + this.txt, null).subscribe((data) =>{
      console.log(data);
    });
  }
}
