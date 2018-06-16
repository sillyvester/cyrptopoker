import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.scss"]
})
export class HomepageComponent implements OnInit {
  constructor() {}


  myStyle: object;
  myParams: object;
  interactivity: object;

  ngOnInit() {
    this.myStyle = {
      position: "fixed",
      width: "100",
      height: "10",
      "z-index": -1,
      top: 0,
      left: 0,
      right: 0,
      bottom: 0
    };

    this.myParams = {
      particles: {
        number: {
          value: 100
        },
        color: {},
        shape: {
          type: "triangle"
        }
      },
      interactivity: {
        detect_on: "canvas",
        events: {
          onhover: {
            enable: false,
            mode: "repulse"
          },
          onclick: {
            enable: true,
            mode: "push"
          },
          resize: false
        }
      }
    };
  }
}
