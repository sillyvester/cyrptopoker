import { Component, OnInit } from "@angular/core";
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { TemplateRef } from '@angular/core';
import { FormsModule} from '@angular/forms';
import {NgSelectModule} from '@ng-select/ng-select';


@Component({
  selector: "app-homepage",
  templateUrl: "./homepage.component.html",
  styleUrls: ["./homepage.component.scss"]
})
export class HomepageComponent implements OnInit {

  myStyle: object;
  myParams: object;
  interactivity: object;
  modalRef: BsModalRef;
  constructor(private modalService: BsModalService) {}
  stakes = ["1/2", "1/3", "2/5"];
  selectedStakes: string[];
  selectedPlayers: string[];
  buyIn: number;

  ngOnInit() {
    this.myStyle = {
      position: "fixed",
      width: "100",
      height: "10",
      "z-index": -10,
      top: 0,
      left: 0,
      right: 0,
      bottom: 0
    };

    this.myParams = {
      particles: {
        number: {
          value: 130
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
            enable: false,
            mode: "push"
          },
          resize: false
        }
      }
    };
  }
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
    console.log("button hit");
  }
  addStakes = (term) => ({id: term, name: term});
}
