import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable({
	providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

	constructor() { }

	intercept(req: HttpRequest<any>, next: HttpHandler) {

		if (localStorage.getItem('id') && localStorage.getItem('token')) {
			req = req.clone({
				setHeaders: {
					'id': localStorage.getItem('id') as string,
					'token': localStorage.getItem('token') as string,
					'Content-Type': 'application/json'
				}
			});
		}

		console.log('Header added');

		return next.handle(req);
	}
}