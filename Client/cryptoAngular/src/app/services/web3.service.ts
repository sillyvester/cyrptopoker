import {BehaviorSubject, Subject,  Observable} from 'rxjs';
import { Injectable, NgZone } from '@angular/core';
import Web3 from 'web3';
import { WindowRefService } from './window-ref.service';
import * as contract from 'truffle-contract';

@Injectable()
export class Web3Service {
  private web3: Web3;
  public MetaCoin: any;
  private accounts: string[];
  private balance;
  public accountsObservable = new Subject<any>();
  constructor(private windowRef: WindowRefService, private ngZone: NgZone) {
    this.setupMetamaskWeb3();
    this.refreshAccounts();
  }

  private setupMetamaskWeb3() {
    if (!this.windowRef.nativeWindow) {
      throw new Error('Cannot get the window');
    }
    if( !this.windowRef.nativeWindow.web3) {
      throw new Error('Not metamask browser');
    }
    this.web3 = new Web3(this.windowRef.nativeWindow.web3.currentProvider);
  }

  private refreshAccounts(){
      this.web3.eth.getAccounts((err,accs) => {
        if(err != null) {
          alert("There was an error fetching your accounts.");
          return;
        }

        // Get the initial account balance so it can be displayed.
        if(accs.length==0) {
          alert("Couldn't get any accounts! Make sure your Ethereum client is configured correctly");
        }

        if(!this.accounts || this.accounts.length!=accs.length
          || this.accounts[0] != accs[0]) {
            console.log( accs);
            this.accounts = accs;
            this.ngZone.run(()=>this.accountsObservable.next(accs));
          }
      });
  }

}
