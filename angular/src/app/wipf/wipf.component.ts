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

  ngOnInit() {
  }


  public test(): void {
    console.log('test');
  }

  public sendMsg(): void {
    this.http.put('http://192.168.2.10:8080/msg/blub', null).subscribe(data => {
      console.log(data);
    });
  }

  public sendMsg2(): void {
    this.http.put('http://localhost:8080/msg/blub', null).subscribe(data => {
      console.log(data);
    });
  }

}
