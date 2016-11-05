package com.wpmac.mzeht.rxjava;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.wpmac.mzeht.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String TAG_FOR_LOGGER = "MainActivity";
    private static final String ERROR = "故意让程序出错";
    private static final String JPG = ".jpg";
    private int mCounter;//循环的计数器

    @BindView(R.id.rxImage)
    ImageView mImageView;
    private Bitmap mManyBitmapSuperposition = null;
    private Canvas mCanvas = null;
    private ProgressBar mProgressBar;
    private EditText mSearchEditText;
    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        initializeLogAndDeviceInfo();
        setTitle("Rxjava");
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initializeLogAndDeviceInfo() {
        Logger.init(TAG_FOR_LOGGER).logLevel(LogLevel.FULL);//Use LogLevel.NONE for the release versions.
    }


    @OnClick(R.id.method0)
    void method0() {

        //1:被观察者,事件源
        //概念解释:RxJava 使用 Observable.create() 方法来创建一个 Observable ，并为它定义事件触发规则

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onNext("!");
//                subscriber.onCompleted();
                subscriber.onError(new Throwable());
                subscriber.onCompleted();
                Logger.d("被观察者-observable->call()->onCompleted()之后是否还有输出");
            }
        });

        /**
         * 可以看到，这里传入了一个 OnSubscribe 对象作为参数。
         * OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
         * 当Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发
         * （对于上面的代码，就是观察者subscriber 将会被调用三次 onNext() 和一次 onCompleted()）。
         * 这样，由被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式。
         */
        //2:观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("观察者-observer:onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("观察者-observer:onError" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Logger.d("观察者-observer:onNext():" + s);
                // getException();//故意让程序出现异常,用于测试onError()方法的执行....
            }
        };

        //3:订阅--被观察者被观察者订阅
        observable.subscribe(observer);
    }


    //---------------------------------------1:快捷创建事件队列 Observable.just(T...)--------------------------------------------------------------

    // create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列，
    // 例如just(T...): 将传入的参数依次发送出来.

    //简化:观察者的创建,RxJava快捷创建事件队列的方法:just(T...):

    /**
     * 简化:观察者的创建
     * {@link #method0()}
     */

    @OnClick(R.id.method1)
    void method1() {


        //实现步骤
        //1:被观察者:
        //2:观察者:
        //3:订阅-被观察者被观察者订阅


        //1:被观察者:
        //just(T...): 将传入的参数依次发送出来
        Observable<String> observable = Observable.just("Hello", "World", "!");
        // 将会依次调用：
        // onNext("Hello");
        // onNext("World");
        // onNext("!");
        // onCompleted();


        //2:观察者:
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("观察者-observer:onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("观察者-observer:onError()");
            }

            @Override
            public void onNext(String s) {
                Logger.d("观察者-observer:onNext():" + s);
                // getException();//故意让程序出现异常,用于测试onError()方法的执行....
            }
        };

        //3:订阅:被观察者被观察者订阅
        observable.subscribe(observer);
    }


    //---------------------------------------2:快捷创建事件队列 Observable.from(T[]) / from(Iterable<? extends T>--------------------------------------------------------------

    /**
     * 简化:观察者的创建: RxJava快捷创建事件队列的方法:just(String[] array) 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来
     * {@link #method1()}
     */

    @OnClick(R.id.method2)
    void method2() {

        //实现步骤
        //1:被观察者
        //2:观察者
        //3:订阅-被观察者被观察者订阅


        String[] array = new String[]{"Hello", "World", "!"};
        //1:被观察者:
        //just(String[] array) 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
        Observable observable = Observable.from(array);
        // 将会依次调用：
        // onNext("Hello");
        // onNext("World");
        // onNext("!");
        // onCompleted();


        //2:观察者
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {
                Logger.d("观察者-observer:onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("观察者-observer:onError()");
            }

            @Override
            public void onNext(Object o) {
                String str = (String) o;
                Logger.d("观察者-observer:onNext():" + str);
                // getException();//故意让程序出现异常,用于测试onError()方法的执行....
            }
        };

        //3:订阅: 被观察者被观察者订阅
        observable.subscribe(observer);

    }


    //---------------------------------------3: subscribe()支持不完整定义的回调--------------------------------------------------------------

    /**
     * 对观察者的简化
     * {@link #method2()}
     * subscribe一个参数的不完整定义的回调
     * subscribe(final Action1<? super T> onNext)
     */

    @OnClick(R.id.method3)
    void method3() {

        String[] array = new String[]{"Hello", "World", "!"};
        //1:被观察者
        Observable observable = Observable.from(array);

        //2:观察者
        Action1 onNextAction = new Action1() {
            @Override
            public void call(Object o) {
                String str = (String) o;
                Logger.d("观察者:call(Object o):" + str);
            }
        };

        //3:订阅-被观察者被观察者订阅
        //subscribe(final Action1<? super T> onNext)
        //自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
    }


    /**
     * 对观察者的简化
     * subscribe两个参数的不完整定义的回调
     * {@link #method3()}
     * subscribe(final Action1<? super T> onNext, final Action1<Throwable> onError)
     */
    @OnClick(R.id.method4)
    void method4() {

        //1:被观察者
        Observable observable = Observable.from(new String[]{"Hello", "World", "!"});

        //2:观察者
        Action1 onNextAction = new Action1() {
            @Override
            public void call(Object o) {
                String str = (String) o;
                Logger.d("观察者:onNextAction:call(Object o):o:" + str);
            }
        };


        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.d("观察者:onErrorAction:call(Throwable throwable):" + throwable.getMessage());
            }
        };


        //3:订阅
        //subscribe(final Action1<? super T> onNext, final Action1<Throwable> onError)
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);


    }

    /**
     * subscribe三个参数的不完整定义的回调
     * subscribe(final Action1<? super T> onNext, final Action1<Throwable> onError, final Action0 onComplete)
     */
    @OnClick(R.id.method5)
    void method5() {
        //1:被观察者
        Observable observable = Observable.from(new String[]{"Hello", "World", "!"});


        //2:观察者
        Action1 onNextAction = new Action1() {
            @Override
            public void call(Object o) {
                String str = (String) o;
                Logger.d("观察者:onNextAction:call():s:" + str);
            }
        };


        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.d("观察者:onErrorAction:call(Throwable throwable):" + throwable.getMessage());
            }
        };


        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Logger.d("观察者:onCompletedAction:call()");
            }
        };


        //3:订阅:被观察者被观察者订阅

        //subscribe(final Action1<? super T> onNext, final Action1<Throwable> onError, final Action0 onComplete)
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

    }

    //---------------------------------------4: Action0和Action1 讲解--------------------------------------------------------------
    /**
     * 肯定有同学对Action0和Action1很困惑,就像当初我刚看到那样子;
     * 那就听听扔物线给大家讲一下:
     *
     * MaLin:扔物线大哥,你能够给我们讲解一下Action0和Action1是什么,以及他们之间的区别吗?
     *
     * 扔物线:大家好,我简单的解释一下:
     * Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的；
     * 由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，
     * 将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。
     * 这样其实也可以看做将 onCompleted() 方法作为参数传进了 subscribe()，相当于其他某些语言中的『闭包』。
     *
     * Action1 也是一个接口，它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；
     * 与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error) 也是单参数无返回值的，
     * 因此 Action1 可以将 onNext(obj) 和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调。
     * 事实上，虽然 Action0 和 Action1 在 API 中使用最广泛，但 RxJava 是提供了多个 ActionX 形式的接口 (例如 Action2, Action3) 的，
     * 它们可以被用以包装不同的无返回值的方法。
     */


    //---------------------------------------5: 休息一下!推荐两个好用的日志查看工具-------------------------------------------------------------

    //1.[logger](https://github.com/orhanobut/logger) | 一个简洁,优雅,功能强大的Android日志输出工具
    //2.[pidcat](https://github.com/JakeWharton/pidcat)|JakeWharton项目一个简洁,优雅的,彩色日志终端查看库|在终端过滤日志信息

    /**
     * 使用com.github.orhanobut:logger 库可以查看当前的线程
     *  ╔════════════════════════════════════════════════════════════════════════════════════════
     D  ║ Thread: main
     D  ╟────────────────────────────────────────────────────────────────────────────────────────
     D  ║ MainActivity$11.onNext  (MainActivity.java:338)
     D  ║    MainActivity$11.onNext  (MainActivity.java:354)
     D  ╟────────────────────────────────────────────────────────────────────────────────────────
     D  ║ 观察者 onNext()
     D  ╚════════════════════════════════════════════════════════════════════════════════════════
     D  ╔════════════════════════════════════════════════════════════════════════════════════════
     D  ║ Thread: main
     D  ╟────────────────────────────────────────────────────────────────────────────────────────
     D  ║ SafeSubscriber.onCompleted  (SafeSubscriber.java:83)
     D  ║    MainActivity$11.onCompleted  (MainActivity.java:341)
     D  ╟────────────────────────────────────────────────────────────────────────────────────────
     D  ║ 观察者 onCompleted()
     D  ╚════════════════════════════════════════════════════════════════════════════════════════
     */

    //---------------------------------------6 线程控制-Scheduler-------------------------------------------------------------

    /**
     * 显示图片
     * 后台线程取数据，主线程显示
     * 加载图片将会发生在 IO 线程，而设置图片则被设定在了主线程。这就意味着，即使加载图片耗费了几十甚至几百毫秒的时间，也不会造成丝毫界面的卡顿。
     * 执行的顺序，progressBar显示》加载图片》观察者OnNext》观察者Oncompelte
     */

    @OnClick(R.id.method6)
    void method6() {

        final int drawableRes = R.mipmap.malin;
        Observable.create(new Observable.OnSubscribe<Drawable>() { //1:被观察者
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Logger.d("被观察者 加载图片");
                //加长进度条显示的时间
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())//事件产生的线程。指定 subscribe() 发生在 IO 线程
                // doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。这里将执行在主线程中
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.VISIBLE);//显示一个等待的ProgressBar--需要在主线程中执行
                            Logger.d("ProgressBar显示");
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 所运行在的线程。或者叫做事件消费的线程
                .subscribe(new Subscriber<Drawable>() {   //3:订阅 //2:观察者
                    @Override
                    public void onCompleted() {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        Logger.d("观察者 onCompleted()，ProgressBar隐藏");
                        Toast.makeText(getApplicationContext(), "观察者 onCompleted()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        Logger.d("观察者 onError()");
                        Toast.makeText(getApplicationContext(), "观察者 onError() " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        Toast.makeText(getApplicationContext(), "观察者 onNext()", Toast.LENGTH_SHORT).show();
                        Logger.d("观察者 onNext()");
                        mImageView.setImageDrawable(drawable);
                    }
                });

    }

    //---------------------------------------7: 变换 map()-------------------------------------------------------------
    @OnClick(R.id.method7)
    void method7() {
        final int drawableRes = R.mipmap.malin;

        //1:被观察者
        Observable.just(drawableRes)//输入类型 int
                .map(new Func1<Integer, Drawable>() {

                    @Override
                    public Drawable call(Integer integer) {// 参数类型 String
                        Logger.d("integer:" + integer);
                        return getResources().getDrawable(integer);
                    }
                })
                .subscribeOn(Schedulers.io())//事件产生的线程。指定 subscribe() 发生在 IO 线程
                //doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。这里将执行在主线程中
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.VISIBLE);//显示一个等待的ProgressBar--需要在主线程中执行
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 所运行在的线程。或者叫做事件消费的线程
                .subscribe(new Subscriber<Drawable>() {  //3:订阅 //2:观察者
                    @Override
                    public void onCompleted() {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        Logger.d("观察者:onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mProgressBar != null) {
                            mProgressBar.setVisibility(View.GONE);
                        }
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Logger.d("观察者:onError(Throwable e):" + e.getMessage());
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        mImageView.setImageDrawable(drawable);
                        Logger.d("观察者:onNext(Drawable drawable):" + drawable.toString());
                    }
                });
    }


    //演示嵌套循环
    @OnClick(R.id.method8)
    void method8() {
        ArrayList<Student> students = DataFactory.getData();
        int size = students.size();
        for (int i = 0; i < size; i++) {
            Logger.d("姓名:" + students.get(i).name);
            int sizeCourses = students.get(i).courses.size();
            for (int j = 0; j < sizeCourses; j++) {
                Logger.d("课程:" + students.get(i).courses.get(j).name);
            }
        }
    }

    /**
     * 需要:依次输入学生的姓名:将每个学生(实体对象)依次发射出去
     * RxJava解决方案:
     * {@link #method8()}
     */

    @OnClick(R.id.method9)
    void method9() {
        //just(T...): 将传入的参数依次发送出来,实现遍历的目的
        Observable.from(DataFactory.getData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        Logger.d("观察者:" + student.name);
                    }
                });
    }


    /**
     * 需要:输出学生的姓名:将每个学生的(姓名)依次发射出去
     * RxJava解决方案
     * {@link #method9()}
     */
    @OnClick(R.id.method10)
    void method10() {

        //1:被观察者

        //2:数据转换

        //3:事件产生的线程。

        //4:事件消费的线程。

        //5:被观察者被观察者订阅

        //6:观察者

        Observable.from(DataFactory.getData())

                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.name;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("观察者:onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("观察者:onError(Throwable e)  " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Logger.d("观察者:onNext(String s) " + s);
                    }
                });

    }

    /**
     * 需要:输出学生的姓名:将每个学生的(姓名)依次发射出去,对method9()的简化
     * RxJava解决方案
     * 输出学生的姓名
     * {@link #method10()}
     */
    void method11() {
        Observable.from(DataFactory.getData())
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.name;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Logger.d("观察者:" + s);
                    }
                });

    }


    //---------------------------------------9: 引入flatmap()-------------------------------------------------------------

    /**
     * 需要:输出每一个学生所有选修的课程
     * 嵌套循环的RxJava解决方案
     * 输出每一个学生选修的课程
     * {@link #method11()}
     */

    @OnClick(R.id.method12)
    void method12() {

        //1:被观察者

        //2:被观察者被观察者订阅

        //3:观察者

        Observable.from(DataFactory.getData())
                .subscribe(new Subscriber<Student>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("观察者:onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("观察者:onError(Throwable e)" + e.getMessage());
                    }

                    @Override
                    public void onNext(Student student) {
                        ArrayList<Course> courses = student.courses;
                        for (Course course : courses) {
                            Logger.d("观察者:" + course.name);
                        }
                    }
                });

    }

    /**
     * 需要:输出每一个学生选修的课程,对method12的简化
     * 嵌套循环的RxJava解决方案
     * {@link #method12()}
     * Student->ArrayList<Course>
     */
    @OnClick(R.id.method13)
    void methoad13() {
        Observable.from(DataFactory.getData())
                .map(new Func1<Student, ArrayList<Course>>() {
                    @Override
                    public ArrayList<Course> call(Student student) {
                        return student.courses;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<Course>>() {
                    @Override
                    public void call(ArrayList<Course> courses) {
                        for (Course c : courses) {
                            Logger.d("观察者：" + c.name);
                        }
                    }
                });

    }


    //---------------------------------------10: flatMap()的使用-------------------------------------------------------------

    /**
     * 需要:输出每一个学生选修的课程,对method13的简化
     * 嵌套循环的RxJava解决方案
     * {@link #method13()}
     * Student -> ArrayList<Course> -> Observable<Course> ->
     */

    void method14() {

        //1:被观察者

        //2:数据转换

        //3:事件产生的线程。

        //4:事件消费的线程。

        //5:被观察者被观察者订阅

        //6:观察者

        // Student->Course
        Observable.from(DataFactory.getData())
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.courses);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Logger.d("观察者:" + course.name);
                    }
                });

    }


}
