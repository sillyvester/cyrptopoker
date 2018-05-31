import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgControlStatus } from '@angular/forms';

export abstract class RepositoryService<T> {

  protected abstract endPoint;

  protected httpOptions  = {
    headers: new HttpHeaders({
      'Content-Type' : 'application/json'

    })
  };

  constructor(protected httpClient: HttpClient) {}

  public get(): Observable<T[]> {
    return this.httpClient.get<T[]>(`${this.endPoint}`, this.httpOptions).pipe(
      catchError(this.handleException)
    );
  }

  public getById(id: number): Observable<T> {
    return this.httpClient.get<T>(`${this.endPoint}/${id}`, this.httpOptions).pipe(
      catchError(this.handleException)
    );
  }

  public add(item: T): Observable<T> {
    console.log("service",item);
    return this.httpClient.post<T>(`${this.endPoint}`, item, this.httpOptions).pipe(
      catchError(this.handleException)
    );
  }



  public update(id: number, item: T): Observable<T> {
    return this.httpClient.put<T>(`${this.endPoint}/${id}`, item, this.httpOptions).pipe(
      catchError(this.handleException)
    );
  }

  protected handleException(exception: any) {
    const message = `${exception.status} : ${exception.statusText}\r\n${exception.message}`;
    alert(message);
    return Observable.throw(exception);
  }
}

