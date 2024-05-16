/*
 * This file is generated by jOOQ.
 */
package com.ssafy.jooq.tables;


import com.ssafy.jooq.FluffitFlupet;
import com.ssafy.jooq.Indexes;
import com.ssafy.jooq.Keys;
import com.ssafy.jooq.tables.records.MemberFlupetRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function15;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row15;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MemberFlupet extends TableImpl<MemberFlupetRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>fluffit_flupet.member_flupet</code>
     */
    public static final MemberFlupet MEMBER_FLUPET = new MemberFlupet();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MemberFlupetRecord> getRecordType() {
        return MemberFlupetRecord.class;
    }

    /**
     * The column <code>fluffit_flupet.member_flupet.id</code>.
     */
    public final TableField<MemberFlupetRecord, UInteger> ID = createField(DSL.name("id"), SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.flupet_id</code>.
     */
    public final TableField<MemberFlupetRecord, UInteger> FLUPET_ID = createField(DSL.name("flupet_id"), SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.member_id</code>.
     */
    public final TableField<MemberFlupetRecord, String> MEMBER_ID = createField(DSL.name("member_id"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.name</code>.
     */
    public final TableField<MemberFlupetRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(10).nullable(false), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.exp</code>.
     */
    public final TableField<MemberFlupetRecord, Integer> EXP = createField(DSL.name("exp"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("0", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.steps</code>.
     */
    public final TableField<MemberFlupetRecord, UInteger> STEPS = createField(DSL.name("steps"), SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(DSL.field("0", SQLDataType.INTEGERUNSIGNED)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.is_dead</code>.
     */
    public final TableField<MemberFlupetRecord, Byte> IS_DEAD = createField(DSL.name("is_dead"), SQLDataType.TINYINT.nullable(false).defaultValue(DSL.field("0", SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.create_time</code>.
     */
    public final TableField<MemberFlupetRecord, LocalDateTime> CREATE_TIME = createField(DSL.name("create_time"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.end_time</code>.
     */
    public final TableField<MemberFlupetRecord, LocalDateTime> END_TIME = createField(DSL.name("end_time"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field("NULL", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.fullness</code>.
     */
    public final TableField<MemberFlupetRecord, Integer> FULLNESS = createField(DSL.name("fullness"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("100", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.health</code>.
     */
    public final TableField<MemberFlupetRecord, Integer> HEALTH = createField(DSL.name("health"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("100", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.pat_cnt</code>.
     */
    public final TableField<MemberFlupetRecord, Integer> PAT_CNT = createField(DSL.name("pat_cnt"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.field("5", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.acha_time</code>.
     */
    public final TableField<MemberFlupetRecord, LocalDateTime> ACHA_TIME = createField(DSL.name("acha_time"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field("NULL", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column
     * <code>fluffit_flupet.member_flupet.fullness_update_time</code>.
     */
    public final TableField<MemberFlupetRecord, LocalDateTime> FULLNESS_UPDATE_TIME = createField(DSL.name("fullness_update_time"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field("NULL", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>fluffit_flupet.member_flupet.health_update_time</code>.
     */
    public final TableField<MemberFlupetRecord, LocalDateTime> HEALTH_UPDATE_TIME = createField(DSL.name("health_update_time"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field("NULL", SQLDataType.LOCALDATETIME)), this, "");

    private MemberFlupet(Name alias, Table<MemberFlupetRecord> aliased) {
        this(alias, aliased, null);
    }

    private MemberFlupet(Name alias, Table<MemberFlupetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>fluffit_flupet.member_flupet</code> table
     * reference
     */
    public MemberFlupet(String alias) {
        this(DSL.name(alias), MEMBER_FLUPET);
    }

    /**
     * Create an aliased <code>fluffit_flupet.member_flupet</code> table
     * reference
     */
    public MemberFlupet(Name alias) {
        this(alias, MEMBER_FLUPET);
    }

    /**
     * Create a <code>fluffit_flupet.member_flupet</code> table reference
     */
    public MemberFlupet() {
        this(DSL.name("member_flupet"), null);
    }

    public <O extends Record> MemberFlupet(Table<O> child, ForeignKey<O, MemberFlupetRecord> key) {
        super(child, key, MEMBER_FLUPET);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : FluffitFlupet.FLUFFIT_FLUPET;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.MEMBER_FLUPET_IDX_MEMBER_ID, Indexes.MEMBER_FLUPET_IDX_MEMBER_ID_IS_DEAD);
    }

    @Override
    public Identity<MemberFlupetRecord, UInteger> getIdentity() {
        return (Identity<MemberFlupetRecord, UInteger>) super.getIdentity();
    }

    @Override
    public UniqueKey<MemberFlupetRecord> getPrimaryKey() {
        return Keys.KEY_MEMBER_FLUPET_PRIMARY;
    }

    @Override
    public List<ForeignKey<MemberFlupetRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_MEMBER_FLUPET_FLUPET_ID);
    }

    private transient Flupet _flupet;

    /**
     * Get the implicit join path to the <code>fluffit_flupet.flupet</code>
     * table.
     */
    public Flupet flupet() {
        if (_flupet == null)
            _flupet = new Flupet(this, Keys.FK_MEMBER_FLUPET_FLUPET_ID);

        return _flupet;
    }

    @Override
    public MemberFlupet as(String alias) {
        return new MemberFlupet(DSL.name(alias), this);
    }

    @Override
    public MemberFlupet as(Name alias) {
        return new MemberFlupet(alias, this);
    }

    @Override
    public MemberFlupet as(Table<?> alias) {
        return new MemberFlupet(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberFlupet rename(String name) {
        return new MemberFlupet(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberFlupet rename(Name name) {
        return new MemberFlupet(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public MemberFlupet rename(Table<?> name) {
        return new MemberFlupet(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row15 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row15<UInteger, UInteger, String, String, Integer, UInteger, Byte, LocalDateTime, LocalDateTime, Integer, Integer, Integer, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function15<? super UInteger, ? super UInteger, ? super String, ? super String, ? super Integer, ? super UInteger, ? super Byte, ? super LocalDateTime, ? super LocalDateTime, ? super Integer, ? super Integer, ? super Integer, ? super LocalDateTime, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function15<? super UInteger, ? super UInteger, ? super String, ? super String, ? super Integer, ? super UInteger, ? super Byte, ? super LocalDateTime, ? super LocalDateTime, ? super Integer, ? super Integer, ? super Integer, ? super LocalDateTime, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}