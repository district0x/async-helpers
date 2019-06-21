(ns district.shared.async-helpers
  (:require [cljs.core.async :as async :refer [chan]])
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
