package com.ocs.sequre

import com.google.common.truth.Truth
import io.reactivex.functions.Function
import io.reactivex.subjects.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Truth.assertThat("5").isEqualTo("5")
    }

    @Test
    fun publish() {
        val subject = PublishSubject.create<Int>()
        subject.subscribe(::println, Throwable::printStackTrace)

        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onError(Throwable("error"))
        subject.onNext(4)
        subject.onNext(5)

        val s: PublishSubject<Int>? = null
        val subjects = s ?: PublishSubject.just(1, 2, 3)
        subjects.subscribe(::println, Throwable::printStackTrace)
    }

    @Test
    fun behavior() {
        val subject = BehaviorSubject.create<Int>()
        subject.onErrorReturn {
            it.hashCode()
        }.subscribe(::println, Throwable::printStackTrace) { println("complete") }

        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onError(Throwable("error"))
        subject.onNext(4)
        subject.onNext(5)
    }

    @Test
    fun replay() {
        val subject = ReplaySubject.create<Int>()
        subject.onErrorReturnItem(Int.SIZE_BYTES)
            .subscribe(::println, Throwable::printStackTrace) { println("complete") }

        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onError(Throwable("error"))
        subject.onNext(4)
        subject.onNext(5)
    }

    @Test
    fun async() {
        val subject = AsyncSubject.create<Int>()
        subject.subscribe(::println, Throwable::printStackTrace)

        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onError(Throwable("error"))
        subject.onNext(4)
        subject.onNext(5)
    }
}
