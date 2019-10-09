(ns district.shared.async-helpers
  (:require [taoensso.timbre]
            [cljs.core]
            [district.shared.error-handling]))

(defmacro promise->
  "Takes `thenable` functions as arguments (i.e. functions returning a JS/Promise) and chains them,
   taking care of error handling
   Example:
   (promise-> (thenable-1)
              (thenable-2))"
  [promise & body]
  `(.catch
    (-> ~promise
        ~@(map (fn [expr] (list '.then expr)) body))
    (fn [error#]
      (taoensso.timbre/error "Promise rejected" (merge {:error error#}
                                                       (ex-data error#)
                                                       ~(district.shared.error-handling/compiletime-info &env &form *ns*))))))

(defmacro defer [& body]
  `(.nextTick
    js/process
    (fn []
      ~@body)))

(defmacro safe-go [& body]
  `(cljs.core.async/go
     (try
       ~@body
       (catch :default e#
         (taoensso.timbre/error "Go block exception"
                                (merge {:error e#}
                                       (ex-data e#)
                                       ~(district.shared.error-handling/compiletime-info &env &form *ns*)))
         e#))))

(defmacro <?
  "Expects `expr` to be a channel or ReadPort which produces a value or
  js/Error, and returns values and throws errors"
  [expr]
  `(let [v-or-err# (cljs.core.async/<! ~expr)]
     (if (cljs.core/instance? js/Error v-or-err#)
       (throw v-or-err#)
       v-or-err#)))
