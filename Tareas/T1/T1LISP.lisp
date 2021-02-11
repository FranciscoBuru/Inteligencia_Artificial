; Para cargar el archivo con las funciones correr 
;(load "T1LISP.lisp" :print t)
; en un lisp ejecutado en el directorio de este archivo. 

;;Inverso lista sin mapcar
(defun B (lst)
	(cond 
		((atom lst) lst)
		(t (append (B (cdr lst)) (list (B (car lst)))))
		
	)
)

;; Suma a profundidad

(defun C(lst)
	(cond 
		((null lst) 0)
		((atom lst) lst)
		(t (+ (C (cdr lst)) (C (car lst))))
	)
)

;;Suma con mapcar
(setq x 0)

(defun D(lst)
	(cond 
		((listp lst) (mapcar #'D lst) x) 
		(t (incf x lst))
	)
)

