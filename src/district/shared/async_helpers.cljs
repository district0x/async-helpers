(ns district.shared.async-helpers
  (:require [cljs.core.async :as async :refer [chan]]
            [cljs-promises.async])
  (:require-macros [district.shared.async-helpers]
                   [cljs.core.async.macros :refer [go]]))

(defn promise? [x]
  (instance? js/Promise x))

(defn resolve! [p value]
  (go
    (while true
      (>! p value))))

(defn promise->chan [js-promise]
  (let [p (chan 1)]
    (-> js-promise
        (.then #(resolve! p %)))
    p))

(defn extend-promises-as-channels! []
  (cljs-promises.async/extend-promises-as-channels! (fn [val] val)
                                                    (fn [err] (js/Error. err))
                                                    js/Promise))
