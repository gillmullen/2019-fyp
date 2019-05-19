declare i32 @printf(i8*, ...) #1
declare i32 @puts(i8*)
@.1arg_str = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

@.print..0 = constant [5 x i8] c"pass\00"
@.print..1 = constant [5 x i8] c"fail\00"
define i32 @main ()
{
%.p.grade = alloca i32
store i32 60, i32* %.p.grade
%.t1 = load i32, i32* %.p.grade
%.t2 = icmp sgt i32 %.t1, 40
br i1 %.t2, label %label1, label %label2
label1:
%.t3 = getelementptr [5 x i8], [5 x i8]* @.print..0, i64 0, i64 0
call i32 @puts (i8* %.t3)
br label %label3
label2:
%.t4 = getelementptr [5 x i8], [5 x i8]* @.print..1, i64 0, i64 0
call i32 @puts (i8* %.t4)
br label %label3
label3:
ret i32 0
}
