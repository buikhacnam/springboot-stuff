package com.buinam.schedulemanger.model.base;

import com.buinam.schedulemanger.dto.ResultsetDemoOne;
import com.buinam.schedulemanger.dto.ResultsetDemoTwo;
import com.buinam.schedulemanger.dto.StudentDTO;

import javax.persistence.*;

@Entity

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "get_result_one",
                classes = {
                        @ConstructorResult(
                                targetClass = ResultsetDemoOne.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "name", type = String.class)
                                })
                }
        ),
        @SqlResultSetMapping(
                name = "get_result_two",
                classes = {
                        @ConstructorResult(
                                targetClass = ResultsetDemoTwo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "title", type = String.class)
                                })
                }
        )
})
public class BaseResultSet {
    @Id
    private Long id;
}

